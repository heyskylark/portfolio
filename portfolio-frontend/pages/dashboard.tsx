import * as React from 'react';
import myHead from '../components/myHead';
import withAuthSync from '../components/withAuthSync';

interface DashboardProps {
  title: string;
}

class Dashboard extends React.Component<DashboardProps> {
  static getInitialProps(): DashboardProps {
    return {
      title: 'User Dashboard',
    };
  }
  render(): JSX.Element {
    return (
      <div>
        <h1>Dashboard!</h1>
      </div>
    );
  }
}

export default withAuthSync(myHead(Dashboard));
