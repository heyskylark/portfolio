import * as React from 'react';
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
  }

  private toggleMobileMenu(event: { preventDefault: () => void }): void {
    event.preventDefault();
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
        <div>
          <Link href="/">
            <h1>Brandon Feist</h1>
          </Link>
        </div>
        <div>
          <button className="block" onClick={this.toggleMobileMenu.bind(this)}>
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
      </nav>
    );
  }
}

export default NavBar;
