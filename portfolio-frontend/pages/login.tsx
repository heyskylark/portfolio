import * as React from 'react';
import { isLoggedIn } from '../utils/auth';
import { contextRedirect } from '../utils/redirect';
import myHead from '../components/myHead';
import { NextPageContext } from 'next';
import { loginWithUsernameAndPassword } from '../utils/auth';

interface LoginProps {
  title: string;
}
interface LoginState {
  username: string;
  password: string;
  error: string;
}

class Login extends React.Component<LoginProps, LoginState> {
  constructor(props: LoginProps) {
    super(props);
    this.state = { username: '', password: '', error: '' };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  static getInitialProps(ctx: NextPageContext): LoginProps {
    if (isLoggedIn(ctx)) {
      contextRedirect(ctx, '/dashboard');
    }
    return {
      title: 'Login',
    };
  }
  handleChange(event: React.ChangeEvent<HTMLInputElement>): void {
    const result = {
      [event.target.name]: event.target.value,
    } as Pick<LoginState, 'username' | 'password' | 'error'>;
    this.setState(result);
  }
  handleSubmit(event: React.FormEvent<HTMLFormElement>): void {
    event.preventDefault();
    const { username, password } = this.state;
    loginWithUsernameAndPassword(username, password).catch(err => {
      // TODO show error message when login fails
      console.log('Login error:', err);
    });
  }
  render(): JSX.Element {
    return (
      <div className="container">
        <div className="login-container">
          <h1 className="login-container__header fs-7 fw-normal">Welcome Back!</h1>
          <form className="login-form" onSubmit={this.handleSubmit}>
            <label className="login-form__label fs-3">Username</label>
            <input
              className="login-form__input"
              type="text"
              id="username"
              name="username"
              value={this.state.username}
              onChange={this.handleChange}
              required
            />
            <label className="login-form__label fs-3">Password</label>
            <input
              className="login-form__input"
              type="password"
              id="password"
              name="password"
              value={this.state.password}
              onChange={this.handleChange}
              required
            />
            <button className="login-form__btn" type="submit">
              Sign In
            </button>
          </form>
        </div>
      </div>
    );
  }
}

export default myHead(Login);
