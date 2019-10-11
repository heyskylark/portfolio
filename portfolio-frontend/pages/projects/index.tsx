import * as React from 'react';
import MyHead from '../../components/myHead';

class Projects extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <MyHead title="Projects" />
        <p>This is the projects page.</p>
      </div>
    );
  }
}

export default Projects;
