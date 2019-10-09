import * as React from 'react';
import Head from 'next/head';

import '../styles/styles.scss';
import NavBar from './navBar';

interface HeadProps {
  title: string;
}

class MyHead extends React.Component<HeadProps> {
  render(): JSX.Element {
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
      </div>
    );
  }
}

export default MyHead;
