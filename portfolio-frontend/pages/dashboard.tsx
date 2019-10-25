import * as React from 'react';
import myHead from '../components/myHead';
import withAuthSync from '../components/withAuthSync';
import ProjectsDashboardTable from '../components/projectsDashboardTable';

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
      <div className="container">
        <ProjectsDashboardTable />
      </div>
    );
  }
}

export default withAuthSync(myHead(Dashboard));
