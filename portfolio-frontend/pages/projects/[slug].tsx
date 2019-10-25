import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import { withRouter } from 'next/router';
import myHead from '../../components/myHead';
import { WithRouterProps } from 'next/dist/client/with-router';
import { NextRouter } from 'next/router';
import { formatDate } from '../../utils/projectUtils';
import ProjectFull from '../../models/ProjectFull';

interface ProjectDescriptionProps {
  title: string;
  project: ProjectFull;
  error?: object;
}

class Project extends React.Component<ProjectDescriptionProps & WithRouterProps> {
  static async getInitialProps(router: NextRouter): Promise<ProjectDescriptionProps> {
    const { slug } = router.query;
    // TODO - make url variable
    return fetch(`http://localhost:8080/v1/projects/${slug}`)
      .then(res => {
        if (res.ok) {
          return res.json();
        } else {
          return Promise.reject(new Error(`There was a problem loading project ${slug}.`));
        }
      })
      .then(data => {
        const projectTitle = `${data.name} | Projects`;
        return Promise.resolve({
          title: projectTitle,
          project: data,
        });
      });
    // TODO - maybe see about error page re-routes or error page catcher
    // .catch(err => {
    //   return {
    //     title: `Error Loading Project`,
    //     error: err,
    //   };
    // });
  }
  render(): JSX.Element {
    const { project } = this.props;
    const { name, description, projectType, imageUrl, projectDate } = project;
    return (
      <div>
        <div className="container">
          <header className="pd-header">
            <h2 className="pd-header__type fs-3 fc-terit fw-normal">
              {formatDate(projectDate)} &middot; {projectType}
            </h2>
            <h1 className="pd-header__title fs-8 fw-heavy fs-wide">{name}</h1>
            <div className="pd-header__img-container">
              <img className="pd-header__img" src={imageUrl} alt={`Image of ${name}`}></img>
            </div>
          </header>
          <section
            className="pd-writeup-container"
            dangerouslySetInnerHTML={{ __html: description }}
          />
        </div>
      </div>
    );
  }
}

export default withRouter(myHead(Project));
