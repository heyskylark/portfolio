import * as React from 'react';
import Project from './projectSummary';
import ProjectSummary from '../models/ProjectSummary';

interface ProjectsTableProps {
  projects: Array<ProjectSummary>;
}

class ProjectsTable extends React.Component<ProjectsTableProps> {
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div className="ps-table">
        {projects.map(project => {
          return (
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
          );
        })}
      </div>
    );
  }
}

export default ProjectsTable;
