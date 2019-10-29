import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import nextCookie from 'next-cookies';
import myHead from '../../components/myHead';
import withAuthSync from '../../components/withAuthSync';
import ProjectsDashboardTable from '../../components/projectsDashboardTable';
import ProjectSummary from '../../models/ProjectSummary';
import { NextPageContext } from 'next';

interface DashboardProps {
  title: string;
  projects: Array<ProjectSummary>;
  token?: string;
  err?: string;
}

class Dashboard extends React.Component<DashboardProps> {
  static async getInitialProps(ctx: NextPageContext): Promise<DashboardProps> {
    const { token } = nextCookie(ctx);
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
          token: token,
          error: 'Hello',
        });
      })
      .catch(err => {
        return {
          title: 'User Dashboard',
          projects: [],
          token: token,
          error: err,
        };
      });
  }
  render(): JSX.Element {
    const { projects, token } = this.props;
    return (
      <div className="container">
        <ProjectsDashboardTable projects={projects} token={token} />
      </div>
    );
  }
}

export default withAuthSync(myHead(Dashboard));
