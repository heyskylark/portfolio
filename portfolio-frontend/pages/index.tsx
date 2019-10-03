import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import Project from '../components/projectSummary';
import ProjectSummary from '../models/ProjectSummary';
import MyHead from '../components/myHead';

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
        <MyHead title="Home" />

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
              slug={project.slug}
            />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;
