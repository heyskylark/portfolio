import * as React from 'react';
import { WithRouterProps } from 'next/dist/client/with-router';
import { NextPageContext } from 'next';
import { auth, login } from '../utils/auth';
import AuthToken from '../models/authToken';

interface TokenProps {
  token?: AuthToken | string;
}

// Gets the display name of a JSX component for dev tools
// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const getDisplayName = (Component: { displayName: any; name: any }): void =>
  Component.displayName || Component.name || 'Component';

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const withAuthSync = (WrappedComponent: any): object => {
  class AuthSync extends React.Component<WithRouterProps & TokenProps> {
    static displayName = `withAuthSync(${getDisplayName(WrappedComponent)})`;

    componentDidMount(): void {
      const { token } = this.props;
      // If AuthToken that means a new token was retrieved from auth server.
      if (token && typeof token !== 'string') {
        login(token);
      }
      // TODO how to know if bad auth code is given and to logout/delete cookies
    }

    static async getInitialProps(ctx: NextPageContext): Promise<object> {
      const { code } = ctx.query;

      let token;
      if (typeof code === 'string') {
        token = await auth(ctx, code);
      } else if (typeof code === 'object') {
        token = await auth(ctx, code[0]);
      } else {
        token = await auth(ctx);
      }

      const componentProps =
        WrappedComponent.getInitialProps && (await WrappedComponent.getInitialProps(ctx));

      return { ...componentProps, token };
    }

    render(): JSX.Element {
      return <WrappedComponent {...this.props} />;
    }
  }

  return AuthSync;
};

export default withAuthSync;
