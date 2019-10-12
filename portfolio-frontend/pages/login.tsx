import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import Router from 'next/router';
import MyHead from '../components/myHead';
import { login } from '../utils/auth';
import BackendError from '../models/BackendError';

interface LoginState {
  username: string;
  password: string;
  error: string;
}

class Login extends React.Component<{}, LoginState> {
  constructor(props: object) {
    super(props);
    this.state = { username: '', password: '', error: '' };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
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

    try {
      const response = await fetch(url, request);
      if (response.ok) {
        login(await response.json());
        Router.push('/');
      } else {
        // https://github.com/developit/unfetch#caveats
        const error = (await response.json()) as BackendError;
        return Promise.reject(error.error_description);
      }
    } catch (error) {
      console.error('You have an error in your code or there are Network issues.', error);
      // throw new Error(error);
    }
  }
  render(): JSX.Element {
    return (
      <div>
        <MyHead title="Login" />
        <div>
          <form onSubmit={this.handleSubmit}>
            <h1>Log in to your account</h1>
            <input
              type="text"
              id="username"
              name="username"
              value={this.state.username}
              placeholder="Username"
              onChange={this.handleChange}
              required
            />
            <input
              type="password"
              id="password"
              name="password"
              value={this.state.password}
              placeholder="Password"
              onChange={this.handleChange}
              required
            />
            <button type="submit">Sign In</button>
          </form>
        </div>
      </div>
    );
  }
}

export default Login;
