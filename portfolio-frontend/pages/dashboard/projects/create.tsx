import * as React from 'react';
import nextCookie from 'next-cookies';
import myHead from '../../../components/myHead';
import withAuthSync from '../../../components/withAuthSync';
import { NextPageContext } from 'next';

interface CreateProjectState {
  name: string;
  image?: File;
  imageUrl: string;
  description: string;
  projectType: string;
  technologies: string;
  projectDate?: number;
}

interface CreateProjectProps {
  token?: string;
}

class CreateProject extends React.Component<CreateProjectProps, CreateProjectState> {
  constructor() {
    super({});
    this.state = {
      name: '',
      imageUrl: '',
      description: '',
      projectType: '',
      technologies: '',
    };
  }
  static getInitialProps(ctx: NextPageContext): CreateProjectProps {
    const { token } = nextCookie(ctx);

    return {
      token: token,
    };
  }
  handleChange(
    event: React.ChangeEvent<HTMLInputElement> | React.FormEvent<HTMLTextAreaElement>,
  ): void {
    const result = {
      [event.target.name]: event.target.value,
    } as Pick<CreateProjectState, 'name' | 'description' | 'projectType'>;
    this.setState(result);
  }
  handleSubmit(event: React.FormEvent<HTMLFormElement>): void {
    event.preventDefault();
    const { token } = this.props;
    // In reality this needs to be two steps
    // Upload the image and get the imageUrl
    // Then upload the project
    // Maybe upload the image in the handleChange step just for images and dont let a submit until image is uploaded
    // TODO - change url to be variable
    const url = 'http://localhost:8080/v1/projects';
    const request = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `bearer ${token}`,
      },
      body: JSON.stringify(this.state),
    };
    fetch(url, request)
      .then(res => {
        if (res.ok) {
          // Re-route to dashboard with okay message
        } else {
          Promise.reject('There was an issue creating the project');
        }
      })
      .catch(err => {
        throw Error(err);
      });
  }
  render(): JSX.Element {
    return (
      <div className="container">
        <h1>Create Project</h1>
        <form onSubmit={this.handleSubmit}>
          <label className="fs-3">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={this.state.name}
            onChange={this.handleChange}
            required
          />
          <label className="fs-3">Image</label>
          <input
            type="file"
            id="image"
            name="image"
            value={this.state.image}
            onChange={this.handleChange}
            required
          />
          <label className="fs-3">Project Type</label>
          <input
            type="text"
            id="projectType"
            name="projectType"
            value={this.state.projectType}
            onChange={this.handleChange}
            required
          />
          <label className="fs-3">Description</label>
          <textarea
            type="text"
            id="projectType"
            name="projectType"
            value={this.state.description}
            onChange={this.handleChange}
            required
          />
          <label className="fs-3">Technologies</label>
          <input
            type="text"
            id="projectType"
            name="projectType"
            value={this.state.technologies}
            onChange={this.handleChange}
            required
          />
          <label className="fs-3">Project Date</label>
          <input
            type="date"
            id="projectType"
            name="projectType"
            value={this.state.projectDate}
            onChange={this.handleChange}
            required
          />
          <button type="submit">Create Project</button>
        </form>
      </div>
    );
  }
}

export default withAuthSync(myHead(CreateProject));
