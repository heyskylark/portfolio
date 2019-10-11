import * as React from 'react';
import MyHead from '../components/myHead';

class Contact extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <MyHead title="Contact" />
        <p>This is the contact page.</p>
      </div>
    );
  }
}

export default Contact;
