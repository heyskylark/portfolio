import * as React from 'react';
import Head from 'next/head';
import NavBar from './navBar';
import { library, config } from '@fortawesome/fontawesome-svg-core';
import { faExternalLinkAlt } from '@fortawesome/free-solid-svg-icons';

import '../styles/styles.scss';

interface HeadProps {
  title: string;
}

class MyHead extends React.Component<HeadProps> {
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
      </div>
    );
  }
}

export default MyHead;
