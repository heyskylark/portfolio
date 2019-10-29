import * as React from 'react';
import Link from 'next/link';
import ProjectSummary from '../models/ProjectSummary';
import ProjectsDashboardTableInfo from './projectsDashboardTableInfo';

interface ProjectsDashboardTableProps {
  projects: Array<ProjectSummary>;
  token?: string;
}

class ProjectsDashboardTable extends React.Component<ProjectsDashboardTableProps> {
  render(): JSX.Element {
    const { projects, token } = this.props;
    return (
      <div>
        <div className="project-dash-table-title">
          <h1 className="project-dash-table-title__title fs-7">Project Table</h1>
          <Link href="/dashboard/projects/create">
            <a
              className="project-dash-table-title__btn fs-3 fs-tight"
              title="Create a new project."
            >
              New Project
            </a>
          </Link>
        </div>
        <div className="project-dash-table">
          <div className="project-dash-table-header">
            {/* <div className="project-dash-table-header__img-header"></div> */}
            <div className="project-dash-table-header__header fw-heavy fs-wide">Name</div>
            {/* <div className="project-dash-table-header__header fw-heavy">Date</div> */}
            <div className="project-dash-table-header__menu-header"></div>
          </div>
          {projects && projects.length > 0 ? (
            projects.map(project => {
              return (
                <ProjectsDashboardTableInfo project={project} token={token} key={project.slug} />
              );
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
