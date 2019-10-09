import * as React from 'react';
import { Router } from 'next/router';
import Link from 'next/link';
import '../styles/styles.scss';

interface NavBarState {
  mobileMenuToggled: boolean;
}

class NavBar extends React.Component<{}, NavBarState> {
  constructor(props: object) {
    super(props);

    this.state = {
      mobileMenuToggled: false,
    };

    // Need this to remove the body fixed class when changing pages
    // The fixed class was remaining after a page change
    Router.events.on('routeChangeComplete', () => {
      document.body.classList.remove('fixed');
      this.setState({
        mobileMenuToggled: false,
      });
    });
  }

  private toggleMobileMenu(event: { preventDefault: () => void }): void {
    event.preventDefault();
    if (this.state.mobileMenuToggled) {
      document.body.classList.remove('fixed');
    } else {
      document.body.classList.add('fixed');
    }
    this.setState({
      mobileMenuToggled: !this.state.mobileMenuToggled,
    });
  }

  render(): JSX.Element {
    const fill = 'hsl(42, 15%, 13%)';
    const width = '24px';
    const height = '24px';
    const viewBox = '0 0 20 20';
    const xmlns = 'http://www.w3.org/2000/svg';
    const xmlnsXlink = 'http://www.w3.org/1999/xlink';
    return (
      <nav className="navbar">
        <div className="mn-header">
          <div>
            <Link href="/">
              <h1>Brandon Feist</h1>
            </Link>
          </div>
          <div>
            <button className="mn-button block" onClick={this.toggleMobileMenu.bind(this)}>
              <svg
                width={width}
                height={height}
                viewBox={viewBox}
                xmlns={xmlns}
                xmlnsXlink={xmlnsXlink}
                className={!this.state.mobileMenuToggled ? 'block' : 'none'}
              >
                <path fill={fill} d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"></path>
              </svg>
              <svg
                width={width}
                height={height}
                viewBox={viewBox}
                xmlns={xmlns}
                xmlnsXlink={xmlnsXlink}
                className={this.state.mobileMenuToggled ? 'block' : 'none'}
              >
                <path
                  fill={fill}
                  d="M10 8.586L2.929 1.515 1.515 2.929 8.586 10l-7.071 7.071 1.414 1.414L10 11.414l7.071 7.071 1.414-1.414L11.414 10l7.071-7.071-1.414-1.414L10 8.586z"
                ></path>
              </svg>
            </button>
          </div>
        </div>
        <div className={'mn-menu-table ' + (this.state.mobileMenuToggled ? '' : 'none')}>
          <div className="mn-menu-container">
            <Link href="/">
              <a className="mn-link fs-6 fw-heavy block">Home</a>
            </Link>
            <Link href="/">
              <a className="mn-link fs-6 fw-heavy block">Projects</a>
            </Link>
            <Link href="/">
              <a className="mn-link fs-6 fw-heavy block">About</a>
            </Link>
            <Link href="/">
              <a className="mn-link fs-6 fw-heavy block">Contact</a>
            </Link>
          </div>
        </div>
      </nav>
    );
  }
}

export default NavBar;
