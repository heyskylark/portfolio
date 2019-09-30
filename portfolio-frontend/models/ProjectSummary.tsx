import Technology from './Technology';

type ProjectSummary = {
  name: string;
  imageUrl: string;
  summary: string;
  projectType: string;
  technologies: Array<Technology>;
  projectDate: Date;
  links: object;
  slug: string;
};

export default ProjectSummary;
