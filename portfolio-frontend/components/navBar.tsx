import * as React from 'react';
import { Router } from 'next/router';
import Link from 'next/link';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { logout } from '../utils/auth';

import '../styles/styles.scss';

interface NavBarProps {
  isLoggedIn: boolean;
}

interface NavBarState {
  mobileMenuToggled: boolean;
}

class NavBar extends React.Component<NavBarProps, NavBarState> {
  constructor(props: NavBarProps) {
    super(props);

    this.state = {
      mobileMenuToggled: false,
    };

    // TODO this is causing a memory leak on unmount and I need to fix it eventually
    // Need this to remove the body fixed class when changing pages
    // The fixed class was remaining after a page change
    Router.events.on('routeChangeComplete', () => {
      document.body.classList.remove('fixed');
      this.setState({
        mobileMenuToggled: false,
      });
    });
  }

  render(): JSX.Element {
    const { isLoggedIn } = this.props;
    const fill = 'hsl(42, 15%, 13%)';
    const width = '24px';
    const height = '24px';
    const viewBox = '0 0 20 20';
    const xmlns = 'http://www.w3.org/2000/svg';
    const xmlnsXlink = 'http://www.w3.org/1999/xlink';
    return (
      <nav className="navbar">
        <div className="navbar__header">
          <div className="navbar__logo">
            <Link href="/">
              <a className="fw-heavy" title="Home Page Logo">
                Brandon Feist
              </a>
            </Link>
          </div>
          <div>
            <button
              className="navbar__mobile-button block"
              onClick={this.toggleMobileMenu.bind(this)}
            >
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
        <div className={'menu-table ' + (this.state.mobileMenuToggled ? '' : 'none')}>
          <div className="menu-table__container">
            <div>
              <Link href="/">
                <a className="menu-table__link fs-6 fw-heavy block" title="Home Page">
                  Home
                </a>
              </Link>
            </div>
            <div>
              <Link href="/projects">
                <a className="menu-table__link fs-6 fw-heavy block" title="My Projects">
                  Projects
                </a>
              </Link>
            </div>
            <div>
              <Link href="/about">
                <a className="menu-table__link fs-6 fw-heavy block" title="About Me">
                  About
                </a>
              </Link>
            </div>
            <div>
              <a
                className="menu-table__link fs-6 fw-heavy block"
                href="https://twitter.com/heyskylark"
                title="Link to my Twitter."
                target="_blank"
              >
                Twitter
                <FontAwesomeIcon
                  className="menu-table__external-icon"
                  icon={['fas', 'external-link-alt']}
                  size="xs"
                />
              </a>
            </div>
            <div>
              <a
                className="menu-table__link fs-6 fw-heavy block"
                href="https://www.github.com/brandonfeist"
                title="Link to my GitHub."
                target="_blank"
              >
                GitHub
                <FontAwesomeIcon
                  className="menu-table__external-icon"
                  icon={['fas', 'external-link-alt']}
                  size="xs"
                />
              </a>
            </div>
            <div>
              <a
                className="menu-table__link fs-6 fw-heavy block"
                href="mailto:feist.brandon@gmail.com"
                title="Email me!"
                target="_blank"
                onClick={this.logout.bind(this)}
              >
                Email
                <FontAwesomeIcon
                  className="menu-table__external-icon"
                  icon={['fas', 'external-link-alt']}
                  size="xs"
                />
              </a>
            </div>
            {isLoggedIn ? (
              <div>
                <Link href="/dashboard">
                  <a className="menu-table__link fs-6 fw-heavy block" title="Dashboard">
                    Dashboard
                  </a>
                </Link>
              </div>
            ) : (
              ''
            )}
            {isLoggedIn ? (
              <div>
                <a
                  className="menu-table__link fs-6 fw-heavy block pointer"
                  title="Logout"
                  onClick={this.logout.bind(this)}
                >
                  Logout
                </a>
              </div>
            ) : (
              ''
            )}
          </div>
        </div>
      </nav>
    );
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

  private logout(event: { preventDefault: () => void }): void {
    event.preventDefault();
    logout();
  }
}

export default NavBar;
