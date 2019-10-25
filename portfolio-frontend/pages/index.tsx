import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import ProjectSummary from '../models/ProjectSummary';
import myHead from '../components/myHead';
import Splash from '../components/splash';
import ProjectsTable from '../components/projectsTable';

interface HomeProps {
  title: string;
  projects: Array<ProjectSummary>;
  error?: string;
}
class Home extends React.Component<HomeProps> {
  static async getInitialProps(): Promise<HomeProps> {
    // TODO - change url to be variable
    return fetch('http://localhost:8080/v1/projects?size=4')
      .then(res => {
        if (res.ok) {
          return res.json();
        } else {
          return Promise.reject(new Error('There was a problem loading the projects.'));
        }
      })
      .then(data => {
        return Promise.resolve({
          title: 'Brandon Feist',
          projects: data.content,
          error: 'Hello',
        });
      })
      .catch(err => {
        return {
          title: 'Brandon Feist',
          projects: [],
          error: err,
        };
      });
  }
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div>
        <Splash />
        <div className="title-container">
          <h2 className="fs-7">Top Projects</h2>
        </div>
        <ProjectsTable projects={projects} />
      </div>
    );
  }
}

export default myHead(Home);
