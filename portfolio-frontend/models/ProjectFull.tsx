import Technology from './Technology';

type ProjectFull = {
  name: string;
  imageUrl: string;
  description: string;
  projectType: string;
  technologies: Array<Technology>;
  projectDate: Date;
  links: object;
  slug: string;
};

export default ProjectFull;
