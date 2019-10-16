import * as React from 'react';

class Footer extends React.Component {
  render(): JSX.Element {
    return (
      <div>
        <div className="border">
          <div className="border__inner"></div>
        </div>
        <footer className="footer">
          <p className="footer__info">
            This website was built using{' '}
            <a href="https://nextjs.org" title="Link to Next.js website." target="_blank">
              Next.js
            </a>{' '}
            and{' '}
            <a href="https://spring.io" title="Link to Spring Boot website." target="_blank">
              Spring Boot
            </a>
            .
          </p>
          <p className="footer__links">
            <a
              className="margin-rt-1"
              href="https://twitter.com/heyskylark"
              title="Link to my Twitter."
              target="_blank"
            >
              Twitter
            </a>
            &nbsp;&middot;&nbsp;
            <a
              className="margin-sd-1"
              href="https://www.github.com/brandonfeist"
              title="Link to my GitHub."
              target="_blank"
            >
              GitHub
            </a>
            &nbsp;&middot;&nbsp;
            <a
              className="margin-lf-1"
              href="mailto:feist.brandon@gmail.com"
              title="Email me!"
              target="_blank"
            >
              Email
            </a>
          </p>
        </footer>
      </div>
    );
  }
}

export default Footer;
