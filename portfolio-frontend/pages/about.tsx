import * as React from 'react';
import MyHead from '../components/myHead';

class About extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <MyHead title="About Me" />
        <p>About Me Page</p>
      </div>
    );
  }
}

export default About;
