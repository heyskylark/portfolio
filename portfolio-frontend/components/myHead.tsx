import * as React from 'react';
import Head from 'next/head';
import NavBar from './navBar';
import { WithRouterProps } from 'next/dist/client/with-router';
import { NextRouter } from 'next/router';
import { library, config } from '@fortawesome/fontawesome-svg-core';
import { faExternalLinkAlt } from '@fortawesome/free-solid-svg-icons';

import '../styles/styles.scss';
import Footer from './footer';

interface HeadProps {
  title: string;
}

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const myHead = (WrappedComponent: any) => {
  class MyHead extends React.Component<HeadProps & WithRouterProps> {
    static async getInitialProps(router: NextRouter): Promise<object> {
      let componentProps = {};
      if (typeof WrappedComponent.getInitialProps === 'function') {
        componentProps = await WrappedComponent.getInitialProps(router);
      }

      return {
        ...componentProps,
      };
    }

    render(): JSX.Element {
      config.autoAddCss = false;
      library.add(faExternalLinkAlt);
      const { title } = this.props;
      return (
        <div>
          <Head>
            <title>{title}</title>
            <link
              href="https://fonts.googleapis.com/css?family=Roboto:400,400i,700,700i&display=swap"
              rel="stylesheet"
            ></link>
          </Head>
          <NavBar />
          <WrappedComponent {...this.props} />
          <Footer />
        </div>
      );
    }
  }

  return MyHead;
};

export default myHead;
