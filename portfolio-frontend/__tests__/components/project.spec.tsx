import * as React from 'react';
import { mount } from 'enzyme';
import * as renderer from 'react-test-renderer';
import Project from '../../components/project';
import Technology from '../../models/Technology';

describe('Component', () => {
  describe('Project', () => {
    it('renders correctly', function() {
      const projectDate: Date = new Date();
      const technologies: Array<Technology> = [{ name: 'React' }];
      const project = renderer
        .create(
          <Project
            name="Test"
            imageUrl="image.url"
            projectType="Personal"
            technologies={technologies}
            projectDate={projectDate}
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
        <Project
          name="Test"
          imageUrl="image.url"
          projectType="Personal"
          technologies={technologies}
          projectDate={projectDate}
        />,
      );
      expect(wrap.find('div').text()).toBe('TestPersonal - 2019');
    });
  });
});
