import * as React from 'react';
import myHead from '../../components/myHead';

interface ProjectsProps {
  title: string;
}

class Projects extends React.Component {
  static getInitialProps(): ProjectsProps {
    return {
      title: 'My Projects',
    };
  }
  render(): JSX.Element {
    return (
      <div>
        <p>This is the projects page.</p>
      </div>
    );
  }
}

export default myHead(Projects);
