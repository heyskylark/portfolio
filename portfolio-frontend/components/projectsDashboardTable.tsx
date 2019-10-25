import * as React from 'react';

class ProjectsDashboardTable extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <div>
          <h1>Project Table</h1>
        </div>
        <div className="project-table">
          <div className="project-table-header">
            <div className="project-table-header__header fw-heavy">Name</div>
            <div className="project-table-header__header fw-heavy">Date</div>
            <div className="project-table-header__header fw-heavy">Technologies</div>
            <div className="project-table-header__menu-header"></div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProjectsDashboardTable;
