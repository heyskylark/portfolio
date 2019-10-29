import * as React from 'react';
import Link from 'next/link';
import fetch from 'isomorphic-unfetch';
import ProjectSummary from '../models/ProjectSummary';
import { formatDate } from '../utils/projectUtils';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface ProjectsDashboardTableInfoProps {
  project: ProjectSummary;
  token?: string;
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
    const { name, projectDate, slug } = project;
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
            'project-dash-popup-wrap ' + (this.state.projectMenuToggled ? 'block' : 'none')
          }
        >
          <div
            className={
              'project-dash-table-popup box-shadow-1 ' +
              (this.state.projectMenuToggled ? 'block' : 'none')
            }
          >
            <Link href={`/dashboard/projects/${slug}/edit`}>
              <a className="project-dash-table-popup__edit fs-4 fw-heavy">Edit</a>
            </Link>
            <button
              className="project-dash-table-popup__delete fs-4 fw-heavy"
              onClick={this.deleteProject.bind(this)}
            >
              Delete
            </button>
          </div>
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

  private deleteProject(event: { preventDefault: () => void }): void {
    event.preventDefault();
    const { project, token } = this.props;
    const { name, slug } = project;
    const url = `http://localhost:8080/v1/projects/${slug}`;
    const request = {
      method: 'DELETE',
      headers: {
        Authorization: `bearer ${token}`,
      },
    };
    fetch(url, request)
      .then(res => {
        if (res.ok) {
          // On delete success the table needs to know to refresh/or remove that project
          //    Maybe do a page reload..
        } else {
          Promise.reject(`There was an issue deleting ${name}`);
        }
      })
      .catch(err => {
        console.log(err);
        // If delete fail popup a toast/notification
      });
    // Change state to show loading/deleting
  }
}

export default ProjectsDashboardTableInfo;
