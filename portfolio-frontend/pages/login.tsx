import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import { isLoggedIn } from '../utils/auth';
import { contextRedirect } from '../utils/redirect';
// import Router from 'next/router';
import myHead from '../components/myHead';
import { NextPageContext } from 'next';
// import { login } from '../utils/auth';
// import BackendError from '../models/BackendError';

interface LoginProps {
  title: string;
  loggedIn: boolean;
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
      loggedIn: isLoggedIn(ctx),
    };
  }
  handleChange(event: React.ChangeEvent<HTMLInputElement>): void {
    const result = {
      [event.target.name]: event.target.value,
    } as Pick<LoginState, 'username' | 'password' | 'error'>;
    this.setState(result);
  }
  async handleSubmit(event: React.FormEvent<HTMLFormElement>): Promise<void> {
    event.preventDefault();
    const { username, password } = this.state;
    // TODO - change this url to be variable depending on env
    // http://localhost:8080/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=testclient
    const url = 'http://localhost:8080/oauth/token';
    const formData = new URLSearchParams();
    formData.append('grant_type', 'password');
    formData.append('username', username);
    formData.append('password', password);
    const request = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: 'Basic dGVzdENsaWVudDp0ZXN0cGFzc3dvcmQ=',
      },
      body: formData,
    };

    fetch(url, request)
      .then(response => {
        if (response.ok) {
          return response;
        } else {
          const error = new Error(response.statusText);
          console.log('error: ', error);
          return Promise.reject(error);
        }
      })
      .then(response => response.json())
      .then(data => {
        console.log('data: ', data);
      })
      .catch(err => {
        console.log('fail: ', err);
      });

    //   try {
    //     const response = await fetch(url, request);
    //     if (response.ok) {
    //       login(await response.json());
    //       Router.push('/');
    //     } else {
    //       // https://github.com/developit/unfetch#caveats
    //       const error = (await response.json()) as BackendError;
    //       return Promise.reject(error.error_description);
    //     }
    //   } catch (error) {
    //     console.error('You have an error in your code or there are Network issues.', error);
    //     // throw new Error(error);
    //   }
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
