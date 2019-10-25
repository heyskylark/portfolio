import * as React from 'react';
import Link from 'next/link';
import { formatDate, compileTechnologies } from '../utils/projectUtils';
import Technology from '../models/Technology';

interface ProjectSummaryProps {
  name: string;
  imageUrl: string;
  projectType: string;
  technologies: Array<Technology>;
  summary?: string;
  projectDate: Date;
  slug: string;
  isLast: boolean;
}

class ProjectSummary extends React.Component<ProjectSummaryProps> {
  render(): JSX.Element {
    const { name, imageUrl, projectType, technologies, projectDate, slug, isLast } = this.props;
    const projectLink = `/projects/${slug}`;
    return (
      <div className={'ps-container' + (isLast ? ' margin-bt-1' : '')}>
        <div>
          <div className="ps-container__title">
            <Link href={projectLink}>
              <a
                className="ps-container__title-link fs-8 fw-heavy"
                title={`Link to ${name} writeup.`}
              >
                {name}
              </a>
            </Link>
          </div>
          <div className="ps-container__info">
            <h3 className="ps-container__tech fs-3 fc-terit fw-normal">
              {compileTechnologies(technologies)}
            </h3>
            <h2 className="fs-3 fc-terit fw-normal">
              {formatDate(projectDate)} &middot; {projectType}
            </h2>
          </div>
        </div>
        <div className="ps-container__img-container">
          <Link href={projectLink}>
            <a title={`Link to ${name} writeup.`}>
              <img className="ps-container__img" src={imageUrl} alt={`Preview of ${name}`}></img>
            </a>
          </Link>
        </div>
      </div>
    );
  }
}

export default ProjectSummary;
