import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import ProjectSummary from '../models/ProjectSummary';
import MyHead from '../components/myHead';
import Splash from '../components/splash';
import ProjectsTable from '../components/projectsTable';

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
        <MyHead title="Brandon Feist" />
        <Splash />
        <ProjectsTable projects={projects} />
      </div>
    );
  }
}

export default Home;
