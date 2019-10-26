import * as React from 'react';
import ProjectSummary from '../models/ProjectSummary';
import ProjectsDashboardTableInfo from './projectsDashboardTableInfo';

interface ProjectsDashboardTableProps {
  projects: Array<ProjectSummary>;
}

class ProjectsDashboardTable extends React.Component<ProjectsDashboardTableProps> {
  render(): JSX.Element {
    const { projects } = this.props;
    return (
      <div>
        <h1 className="table-title fs-7">Project Table</h1>
        <div className="project-dash-table box-shadow-0">
          <div className="project-dash-table-header">
            {/* <div className="project-dash-table-header__img-header"></div> */}
            <div className="project-dash-table-header__header fw-heavy fs-wide">Name</div>
            {/* <div className="project-dash-table-header__header fw-heavy">Date</div> */}
            <div className="project-dash-table-header__menu-header"></div>
          </div>
          {projects && projects.length > 0 ? (
            projects.map(project => {
              return <ProjectsDashboardTableInfo project={project} key={project.slug} />;
            })
          ) : (
            <div className="project-dash-table-body project-dash-table-body--empty">
              <h2>No projects exist.</h2>
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default ProjectsDashboardTable;
