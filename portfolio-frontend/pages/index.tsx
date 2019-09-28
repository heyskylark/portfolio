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
          <link
            href="https://fonts.googleapis.com/css?family=Roboto:400,400i,700,700i&display=swap"
            rel="stylesheet"
          ></link>
        </Head>

        <div className="ps-table">
          <Project
            name="Test Project Super Nova, Hack"
            imageUrl="https://www.filtelescu.com/img/SmartHomeProPic.png"
            projectType="Personal"
            technologies={technologies}
            summary="This is a test summary..."
            projectDate={projectDate}
            selfLink="https://localhost:8080/projects/test-project"
          />
          <Project
            name="Test Project Super Nova, Hack"
            imageUrl="https://www.filtelescu.com/img/SmartHomeProPic.png"
            projectType="Personal"
            technologies={technologies}
            summary="This is a test summary..."
            projectDate={projectDate}
            selfLink="https://localhost:8080/projects/test-project"
          />
        </div>
      </div>
    );
  }
}

export default Home;
