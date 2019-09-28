import * as React from 'react';
import Head from 'next/head';
import Project from '../components/project';
import Technology from '../models/Technology';
import '../styles/styles.scss';

class Home extends React.Component {
  render(): JSX.Element {
    const projectDate: Date = new Date();
    const technologies: Array<Technology> = [{ name: 'React' }, { name: 'JavaScript' }];
    return (
      <div>
        <Head>
          <title>Home</title>
        </Head>

        <Project
          name="Test"
          imageUrl="image.url"
          projectType="Personal"
          technologies={technologies}
          projectDate={projectDate}
        />
      </div>
    );
  }
}

export default Home;
