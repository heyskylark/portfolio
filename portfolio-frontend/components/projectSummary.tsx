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
}

class ProjectSummary extends React.Component<ProjectSummaryProps> {
  render(): JSX.Element {
    const { name, imageUrl, projectType, technologies, projectDate, slug } = this.props;
    const projectLink = `/projects/${slug}`;
    return (
      <div className="ps-container">
        <div>
          <Link href={projectLink}>
            <h1 className="ps-title fs-7 fw-heavy fs-wide">{name}</h1>
          </Link>
          <div className="ps-title-div"></div>
          <h3 className="ps-tech fs-3 fc-terit fw-normal">{compileTechnologies(technologies)}</h3>
          <h2 className="ps-type fs-3 fc-terit fw-normal">
            {formatDate(projectDate)} &middot; {projectType}
          </h2>
        </div>
        <div className="ps-img-container">
          <Link href={projectLink}>
            <img className="ps-img" src={imageUrl} alt={`Preview of ${name}`}></img>
          </Link>
        </div>
      </div>
    );
  }
}

export default ProjectSummary;
