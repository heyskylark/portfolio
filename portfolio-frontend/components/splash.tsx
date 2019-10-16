import * as React from 'react';

class Splash extends React.Component {
  render(): JSX.Element {
    return (
      <div className="splash-container">
        <div className="splash-container__inner-container">
          <h1 className="splash-container__header fs-11 fs-tight">Something Douchey.</h1>
          <div className="splash-container__text">
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
      </div>
    );
  }
}

export default Splash;
