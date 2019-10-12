import * as React from 'react';
import fetch from 'isomorphic-unfetch';
import { withRouter } from 'next/router';
import myHead from '../../components/myHead';
import { WithRouterProps } from 'next/dist/client/with-router';
import { formatDate } from '../../utils/projectUtils';
import ProjectFull from '../../models/ProjectFull';

interface ProjectDescriptionProps {
  title: string;
  project: ProjectFull;
  error?: object;
}

class Project extends React.Component<ProjectDescriptionProps & WithRouterProps> {
  static async getInitialProps(router: {
    query: { slug: string };
  }): Promise<ProjectDescriptionProps> {
    const { slug } = router.query;
    try {
      // TODO change url to be variable
      const res = await fetch(`http://localhost:8080/v1/projects/${slug}`);
      const data = await res.json();

      if (res.status == 200) {
        const projectTitle = `${data.name} | Projects`;
        return {
          title: projectTitle,
          project: data,
        };
      } else {
        throw new Error(`${res.status}`);
      }
    } catch (err) {
      console.log(err);
      throw new Error(err);
    }
  }
  render(): JSX.Element {
    const { project } = this.props;
    const { name, description, projectType, imageUrl, projectDate } = project;

    return (
      <div>
        <div className="pd-container">
          <header className="pd-header">
            <h2 className="pd-type fs-3 fc-terit fw-normal">
              {formatDate(projectDate)} &middot; {projectType}
            </h2>
            <h1 className="pd-title fs-8 fw-heavy fs-wide">{name}</h1>
            <div className="pd-img-container">
              <img className="ps-img" src={imageUrl} alt={`Image of ${name}`}></img>
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
