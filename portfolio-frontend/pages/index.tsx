import * as React from 'react';
import Head from 'next/head';
import Project from '../components/project';
import ProjectSummary from '../models/ProjectSummary';

import '../styles/styles.scss';

interface HomeProps {
  projects: Array<ProjectSummary>;
  error?: object;
}
class Home extends React.Component<HomeProps> {
  static async getInitialProps(): Promise<HomeProps> {
    try {
      const res = await fetch('http://localhost:8080/v1/projects');
      const data = await res.json();

      if (res.status == 200) {
        return {
          projects: data.content,
        };
      } else {
        return { projects: [] };
      }
    } catch (err) {
      console.log(err);
      return { projects: [], error: err };
    }
  }
  render(): JSX.Element {
    const { projects } = this.props;
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
          {projects.map(project => (
            <Project
              key={project.slug}
              name={project.name}
              imageUrl={project.imageUrl}
              projectType={project.projectType}
              technologies={project.technologies}
              summary={project.summary}
              projectDate={new Date(project.projectDate)}
              selfLink="https://localhost:8080/projects/test-project"
            />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;
