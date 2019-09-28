import * as React from 'react';
import Technology from '../models/Technology';

interface ProjectProps {
  name: string;
  imageUrl: string;
  projectType: string;
  technologies: Array<Technology>;
  summary?: string;
  projectDate: Date;
}

class Project extends React.Component<ProjectProps> {
  render(): JSX.Element {
    const { name, imageUrl, projectType, technologies, projectDate } = this.props;
    return (
      <div>
        <h1>{name}</h1>
        <h2>{this.compileTechnologies(technologies)}</h2>
        <h2>
          {projectType} - {projectDate.getFullYear()}
        </h2>
        <img src={imageUrl} alt={`Preview of ${name}`}></img>
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
