import * as React from 'react';

class Footer extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <div className="border">
          <div className="border-inner"></div>
        </div>
        <footer>
          <p>This is the footer.</p>
        </footer>
      </div>
    );
  }
}

export default Footer;
