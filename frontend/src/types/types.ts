import { FileRouteTypes } from "@/routeTree.gen";

export type SERVICE_TYPE = "contestants" | "scores";

export interface IRoute {
  path: FileRouteTypes["fullPaths"];
  name: string;
  disabled?: boolean;
  icon?: JSX.Element;
  secondary?: boolean;
  collapse?: boolean;
  items?: IRoute[];
  rightElement?: boolean;
  invisible?: boolean;
}

// Data from a Contestant File
export type GetContestantsDto = {
  records: {
    _id: string;
    data: Record<string, string>;
  }[];
  count: number;
  filename: string;
};

// Data from a Score File
export type GetScoresDto = {
  _id: {
    filename: string;
    competition_date: string;
  };

  count: number;

  records: {
    _id: string;
    data: Record<string, string>;
  }[];
};
