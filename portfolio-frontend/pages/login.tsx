import * as React from 'react';
import MyHead from '../components/myHead';
import { login } from '../utils/auth';

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
    const username = this.state.username;
    const password = this.state.password;

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username }),
      });
      if (response.ok) {
        const { token } = await response.json();
        login({ token });
      } else {
        console.log('Login failed.');
        // https://github.com/developit/unfetch#caveats
        const error = new Error(response.statusText);
        error.response = response;
        return Promise.reject(error);
      }
    } catch (error) {
      console.error('You have an error in your code or there are Network issues.', error);
      throw new Error(error);
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
