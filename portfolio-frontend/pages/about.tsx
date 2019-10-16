import * as React from 'react';
import myHead from '../components/myHead';

interface AboutProps {
  title: string;
}

class About extends React.Component<AboutProps> {
  static getInitialProps(): AboutProps {
    return {
      title: 'About Me',
    };
  }
  render(): JSX.Element {
    return (
      <div>
        <div className="container">
          <div className="about-container">
            <img
              className="about-img"
              src="https://scontent-sjc3-1.xx.fbcdn.net/v/t1.0-9/40199357_10217175120355267_281514696568209408_o.jpg?_nc_cat=103&_nc_oc=AQmYcAuUl0ETcUV6PjWtl5eiKs560HHDkwxHYkLTHRb29T19MeOpVCD1rQktVHcEzOzqc0VcGCPmmc2frUAwTazj&_nc_ht=scontent-sjc3-1.xx&oh=4caa957600e6cdbe6fd672d574830b72&oe=5E2D824E"
              alt="A photo of Brandon Feist."
            />
            <p className="about-container__paragraph">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
              exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
              dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
              mollit anim id est laborum.
            </p>
            <p className="about-container__paragraph margin-bt-none">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
              exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
              dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
              mollit anim id est laborum.
            </p>
          </div>
        </div>
      </div>
    );
  }
}

export default myHead(About);
