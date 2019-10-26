import * as React from 'react';
import ProjectSummary from '../models/ProjectSummary';
import { formatDate } from '../utils/projectUtils';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface ProjectsDashboardTableInfoProps {
  project: ProjectSummary;
}

interface ProjectsDashboardTableInfoState {
  projectMenuToggled: boolean;
}

class ProjectsDashboardTableInfo extends React.Component<
  ProjectsDashboardTableInfoProps,
  ProjectsDashboardTableInfoState
> {
  constructor(props: ProjectsDashboardTableInfoProps) {
    super(props);

    this.state = {
      projectMenuToggled: false,
    };
  }

  render(): JSX.Element {
    const { project } = this.props;
    const { name, projectDate } = project;
    const date = formatDate(projectDate, 'MMM DD, YYYY');
    return (
      <div className="project-dash-table-info">
        {/* <div className="project-dash-table-info__img-header"></div> */}
        <div className="project-dash-table-info__header">
          <p className="project-dash-table-info__name fs-tight fc-main">{name}</p>
          <p className="project-dash-table-info__date fs-tight fc-terit">{date}</p>
        </div>
        {/* <div className="project-dash-table-info__header">{projectDate}</div> */}
        <div className="project-dash-table-info__menu">
          <button
            className="project-dash-table-info__menu-icon fc-terit"
            onClick={this.toggleMenuButton.bind(this)}
          >
            <FontAwesomeIcon icon={['fas', 'ellipsis-h']} size="lg" />
          </button>
        </div>
        <div
          className={
            'project-dash-table-popup box-shadow-1 ' +
            (!this.state.projectMenuToggled ? 'block' : 'none')
          }
        >
          <button className="project-dash-table-popup__edit fs-3">Edit</button>
          <button className="project-dash-table-popup__delete fs-3">Delete</button>
        </div>
      </div>
    );
  }

  private toggleMenuButton(event: { preventDefault: () => void }): void {
    event.preventDefault();
    this.setState({
      projectMenuToggled: !this.state.projectMenuToggled,
    });
  }
}

export default ProjectsDashboardTableInfo;
