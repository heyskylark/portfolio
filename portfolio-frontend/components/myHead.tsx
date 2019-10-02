import * as React from 'react';
import Head from 'next/head';

import '../styles/styles.scss';

interface HeadProps {
  title: string;
}

class MyHead extends React.Component<HeadProps> {
  render(): JSX.Element {
    const { title } = this.props;
    return (
      <Head>
        <title>{title}</title>
        <link
          href="https://fonts.googleapis.com/css?family=Roboto:400,400i,700,700i&display=swap"
          rel="stylesheet"
        ></link>
      </Head>
    );
  }
}

export default MyHead;
