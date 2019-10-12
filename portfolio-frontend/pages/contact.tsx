import * as React from 'react';
import myHead from '../components/myHead';

interface ContactProps {
  title: string;
}

class Contact extends React.Component {
  static getInitialProps(): ContactProps {
    return {
      title: 'Contact',
    };
  }
  render(): JSX.Element {
    return (
      <div>
        <p>This is the contact page.</p>
      </div>
    );
  }
}

export default myHead(Contact);
