import * as React from 'react';
import myHead from '../components/myHead';
import withAuthSync from '../components/withAuthSync';
import ProjectsDashboardTable from '../components/projectsDashboardTable';
import ProjectSummary from '../models/ProjectSummary';

interface DashboardProps {
  title: string;
  projects: Array<ProjectSummary>;
  err?: string;
}

class Dashboard extends React.Component<DashboardProps> {
  static async getInitialProps(): Promise<DashboardProps> {
    // TODO - change url to be variable
    return fetch('http://localhost:8080/v1/projects')
      .then(res => {
        if (res.ok) {
          return res.json();
        } else {
          return Promise.reject(new Error('There was a problem loading the projects.'));
        }
      })
      .then(data => {
        return Promise.resolve({
          title: 'User Dashboard',
          projects: data.content,
          error: 'Hello',
        });
      })
      .catch(err => {
        return {
          title: 'User Dashboard',
          projects: [],
          error: err,
        };
      });
  }
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div className="container">
        <ProjectsDashboardTable projects={projects} />
      </div>
    );
  }
}

export default withAuthSync(myHead(Dashboard));
