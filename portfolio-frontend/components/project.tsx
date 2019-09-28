import * as React from 'react';
import Technology from '../models/Technology';

interface ProjectProps {
  name: string;
  imageUrl: string;
  projectType: string;
  technologies: Array<Technology>;
  summary?: string;
  projectDate: Date;
  selfLink: string;
}

class Project extends React.Component<ProjectProps> {
  render(): JSX.Element {
    const { name, imageUrl, projectType, technologies, summary, projectDate } = this.props;
    return (
      <div className="ps-container">
        <div>
          <h1 className="ps-title fs-7">{name}</h1>
          <h2 className="ps-type fs-5 fw-heavy fc-second">
            {projectType} - {projectDate.getFullYear()}
          </h2>
          <h3 className="ps-tech fs-4 fw-normal fc-second">
            {this.compileTechnologies(technologies)}
          </h3>
          {summary ? <p className="ps-summary fs-4 fc-terit">{summary}</p> : ''}
        </div>
        <div className="ps-img-container">
          <img className="ps-img" src={imageUrl} alt={`Preview of ${name}`}></img>
        </div>
      </div>
    );
  }

  private compileTechnologies(technologies: Array<Technology>): string {
    let techString = '';
    for (let i = 0; i < technologies.length; i++) {
      techString +=
        i !== technologies.length - 1 ? `${technologies[i].name} - ` : technologies[i].name;
    }
    return techString;
  }
}

export default Project;
