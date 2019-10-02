import * as React from 'react';
import { mount } from 'enzyme';
import * as renderer from 'react-test-renderer';
import ProjectSummary from '../../components/projectSummary';
import Technology from '../../models/Technology';

describe('Component', () => {
  describe('Project', () => {
    it('renders correctly', function() {
      const projectDate: Date = new Date();
      const technologies: Array<Technology> = [{ name: 'React' }];
      const project = renderer
        .create(
          <ProjectSummary
            name="Test"
            imageUrl="image.url"
            projectType="Personal"
            summary="This is a test summary..."
            technologies={technologies}
            projectDate={projectDate}
            slug="test-slug"
          />,
        )
        .toJSON();

      expect(project).toMatchSnapshot();
    });
    it('should render Project props correctly', function() {
      // Things to render: name, type, tech, summary, image url, project date
    });
    it('should render without throwing an error', function() {
      const projectDate: Date = new Date();
      const technologies: Array<Technology> = [{ name: 'React' }];
      const wrap = mount(
        <ProjectSummary
          name="Test"
          imageUrl="image.url"
          projectType="Personal"
          summary="This is a test summary..."
          technologies={technologies}
          projectDate={projectDate}
          slug="test-slug"
        />,
      );
      expect(wrap.find('div').text()).toBe('TestPersonal - 2019');
    });
  });
});
