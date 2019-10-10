import * as React from 'react';

class Splash extends React.Component {
  render(): JSX.Element {
    return (
      <div className="splash-container">
        <h1 className="splash-header fs-11 fs-tight">Something Douchey.</h1>
        <div className="splash-text">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua.
          </p>
          <p>
            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat
            nulla pariatur.
          </p>
        </div>
      </div>
    );
  }
}

export default Splash;
