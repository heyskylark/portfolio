import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import ProjectSummary from '../models/ProjectSummary';
import myHead from '../components/myHead';
import Splash from '../components/splash';
import ProjectsTable from '../components/projectsTable';

interface HomeProps {
  title: string;
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
          title: 'Brandon Feist',
          projects: data.content,
        };
      } else {
        return {
          title: 'Brandon Feist',
          projects: [],
        };
      }
    } catch (err) {
      return {
        title: 'Brandon Feist',
        projects: [],
        error: err,
      };
    }
  }
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div>
        <Splash />
        <ProjectsTable projects={projects} />
      </div>
    );
  }
}

export default myHead(Home);
