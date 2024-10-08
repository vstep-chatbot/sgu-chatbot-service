import {
    FileRoutesByFullPath,
    FileRoutesById,
    FileRouteTypes,
} from "@/routeTree.gen";

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

type MyRecord = {
    id: string;
    import_from?: string;
    type: string;
    citizen_id?: string;
    import_date?: string;
    data?: Record<string, string>;
};

export type ContestantRecord = MyRecord & {
    type: "contestant";
    competition_date: string;
};
