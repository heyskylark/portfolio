import * as React from 'react';
import myHead from '../../components/myHead';
import ProjectSummary from '../../models/ProjectSummary';
import ProjectsTable from '../../components/projectsTable';

interface ProjectsProps {
  title: string;
  projects: Array<ProjectSummary>;
  error?: object;
}

class Projects extends React.Component<ProjectsProps> {
  static async getInitialProps(): Promise<ProjectsProps> {
    // TODO - make url variable
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
          title: 'My Projects',
          projects: data.content,
        });
      })
      .catch(err => {
        return {
          title: 'My Projects',
          projects: [],
          error: err,
        };
      });
  }
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div>
        <div className="title-container">
          <h1>My Projects</h1>
        </div>
        <div className="project-table-header">
          <p>
            This is the projects page. This is a description about my most valuable project. Why
            don't you check out the rest below!
          </p>
          <p>This is a second paragraph since most likely I will have more than one paragraph.</p>
        </div>
        <div className="border">
          <div className="border__inner"></div>
        </div>
        <ProjectsTable projects={projects} />
      </div>
    );
  }
}

export default myHead(Projects);
