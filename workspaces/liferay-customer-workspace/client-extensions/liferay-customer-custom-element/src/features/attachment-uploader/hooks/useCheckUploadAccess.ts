import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

interface Props {
  loading: boolean;
  hasAccess: boolean | null;
}

export default function useCheckUploadAccess(): Props {
  const { ticketId } = useParams();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);
  const [hasAccess, setHasAccess] = useState<boolean | null>(null);

  useEffect(() => {
    if (!ticketId) {
      setLoading(false);
      return;
    }

    const controller = new AbortController();

    const fetchAccess = async () => {
      try {
        const response = await fetch(
          `/o/customer/tickets/${ticketId}/ticket-attachments/upload-access-check`,
          {
            headers: {
              'Content-Type': 'application/json',
            },
            signal: controller.signal,
          }
        );

        if (response.ok) {
          setHasAccess(true);
        }
        else {
          const errorCode = await response.text();

          switch (errorCode) {
            case 'FORBIDDEN_ACCESS':
              navigate(`/${ticketId}/forbidden-access`);
              break;
            case 'TICKET_IS_CLOSED':
              navigate(`/${ticketId}/invalid-ticket-number`);
              break;
            case 'INVALID_TICKET_NUMBER':
              navigate(`/invalid-ticket-number`);
              break;
            case 'ZENDESK_ORGANIZATION_ERROR':
            case 'UNEXPECTED_ERROR':
            default:
              navigate(`/${ticketId}/unexpected-error`);
          }

          setHasAccess(false);
        }
      }
      catch (error) {
        if (!controller.signal.aborted) {
          navigate(`/${ticketId}/unexpected-error`);
        }
      }
      finally {
        setLoading(false);
      }
    };

    fetchAccess();

    return () => {
      controller.abort();
    };
  }, [ticketId, navigate]);

  return { loading, hasAccess };
}
