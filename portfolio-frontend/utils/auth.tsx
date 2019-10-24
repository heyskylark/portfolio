/* eslint-disable @typescript-eslint/camelcase */
import { NextPageContext } from 'next';
import Router from 'next/router';
import fetch from 'isomorphic-unfetch';
import nextCookie from 'next-cookies';
import cookie from 'js-cookie';
import { contextRedirect } from '../utils/redirect';
import AuthToken from '../models/authToken';
import NetworkError from '../models/NetworkError';

// TODO - change this url to be variable depending on env
const TOKEN_URL = 'http://localhost:8080/oauth/token';

export function isLoggedIn(ctx: NextPageContext): boolean {
  const { token } = nextCookie(ctx);
  return !!token;
}

export function login(token: AuthToken): void {
  const { access_token, jti } = token;
  cookie.set('token', access_token);
  cookie.set('tokenId', jti);
  Router.push('/dashboard');
}

export function logout(): void {
  cookie.remove('token');
  cookie.remove('tokenId');
  Router.push('/login');
}

export async function loginWithUsernameAndPassword(
  username: string,
  password: string,
): Promise<void | NetworkError> {
  const formData = new URLSearchParams();
  formData.append('grant_type', 'password');
  formData.append('username', username);
  formData.append('password', password);
  formData.append('client_id', 'testclient'); // TODO Make variable
  const request = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: formData,
  };

  return fetch(TOKEN_URL, request)
    .then(res => {
      if (res.ok) {
        return res.json();
      } else {
        return Promise.reject('Invalid Login.');
      }
    })
    .then(data => {
      console.log('data ', data);
      login(data);
      return Promise.resolve();
    })
    .catch(err => {
      return Promise.reject(err);
    });
}

async function getTokenUsingCodeGrant(grantCode: string): Promise<AuthToken | NetworkError> {
  //TODO make url and redirectUrl variable
  const redirectUrl = 'http://localhost:3000/dashboard';
  const formData = new URLSearchParams();
  formData.append('grant_type', 'authorization_code');
  //TODO make client_id variable
  formData.append('client_id', 'testclient');
  formData.append('redirect_uri', redirectUrl);
  formData.append('code', grantCode);
  const request = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: formData,
  };
  const res = await fetch(TOKEN_URL, request);
  const data = await res.json();

  try {
    if (res.ok) {
      return data;
    } else {
      return {
        status: res.status,
        errorDescription: data.error_description,
      };
    }
  } catch (err) {
    return {
      status: res.status,
      errorDescription: data.error_description,
    };
  }
}

export function auth(ctx: NextPageContext): string | undefined {
  const { token } = nextCookie(ctx);

  if (!token) {
    contextRedirect(ctx, '/login');
    return;
  }

  return token;
}
