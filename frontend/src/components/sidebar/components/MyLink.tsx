import {IRoute} from "@/types/types";
import {
    Link,
    rootRouteId,
    useLocation,
    useMatch,
} from "@tanstack/react-router";

type MyLinkProps = {
    route: IRoute;
};

export default function MyLink({route}: MyLinkProps) {
    const location = useLocation();

    const isActive = location.pathname === route.path;

    return (
        <div>
            <div
                className={`flex w-full max-w-full items-center justify-between rounded-lg py-3 pl-8 ${
                    isActive
                        ? "bg-primary/90 font-semibold text-primary-foreground/90 dark:bg-white dark:text-zinc-950"
                        : "font-medium text-zinc-950 dark:text-zinc-400"
                }`}
            >
                <Link
                    from="/"
                    to={route.path}
                    className="w-full"
                    disabled={route.disabled}
                >
                    <div className="w-full items-center justify-center">
                        <div className="flex w-full items-center justify-center">
                            <div
                                className={`text mr-3 mt-1.5 ${
                                    isActive
                                        ? "font-semibold text-white dark:text-zinc-950"
                                        : "text-zinc-950 dark:text-white"
                                } `}
                            >
                                {route.icon}
                            </div>
                            <p
                                className={`mr-auto text-sm ${
                                    isActive
                                        ? "font-semibold text-white dark:text-zinc-950"
                                        : "font-medium text-zinc-950 dark:text-zinc-400"
                                }`}
                            >
                                {route.name}
                            </p>
                        </div>
                    </div>
                </Link>
            </div>
        </div>
    );
}
