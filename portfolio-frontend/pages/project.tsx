import * as React from 'react';
import MyHead from '../components/myHead';

class Project extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <MyHead title="My Project" />
        Project...
      </div>
    );
  }
}

export default Project;
